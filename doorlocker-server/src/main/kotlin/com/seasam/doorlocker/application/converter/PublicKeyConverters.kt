package com.seasam.doorlocker.application.converter

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils.decodeFromString
import org.springframework.util.Base64Utils.encodeToString
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.regex.Pattern.compile

// Format: `key in Base64`::`algorithm`
const val KEY_STRING_REGEX = "(.*?)::(.*?)$"
const val KEY_STRING = "%s::%s"

@Component
@ReadingConverter
class StringToPublicKeyConverter : Converter<String, PublicKey> {

    override fun convert(stringifiedKey: String): PublicKey? {
        val matcher = compile(KEY_STRING_REGEX).matcher(stringifiedKey)

        if (!matcher.find())
            return null

        val keyEncodedBase64 = matcher.group(1)
        val keyAlgorithm = matcher.group(2)

        val keySpec = X509EncodedKeySpec(decodeFromString(keyEncodedBase64))
        val keyFactory = KeyFactory.getInstance(keyAlgorithm)
        return keyFactory.generatePublic(keySpec)
    }
}

@Component
class StringToPublicKeyDeserializer(private val converter: Converter<String, PublicKey>)
        : StdDeserializer<PublicKey>(PublicKey::class.java) {

    override fun deserialize(parser: JsonParser, ctx: DeserializationContext)
        = converter.convert(parser.text)

}

@Component
@WritingConverter
class PublicKeyToStringConverter : Converter<PublicKey, String> {

    override fun convert(key: PublicKey): String {
        return KEY_STRING.format(encodeToString(key.encoded), key.algorithm)
    }
}

@Component
class PublicKeyToStringSerializer(private val converter: Converter<PublicKey, String>)
        : StdSerializer<PublicKey>(PublicKey::class.java) {

    override fun serialize(value: PublicKey, generator: JsonGenerator, provider: SerializerProvider)
        = generator.writeString(converter.convert(value))
}
