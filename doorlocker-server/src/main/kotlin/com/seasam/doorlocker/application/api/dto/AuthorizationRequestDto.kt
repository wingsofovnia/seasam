package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.ThingId
import java.security.PublicKey
import javax.validation.constraints.NotBlank

data class AuthorizationRequestDto(@NotBlank val deviceKey: PublicKey,
                                   @NotBlank val thingId: ThingId,
                                   @NotBlank val nonce: String)
