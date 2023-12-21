package com.guntur.sehatinaja.data.remote.response

import com.google.gson.annotations.SerializedName

data class MlResponse(

	@field:SerializedName("predict")
	val predict: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
