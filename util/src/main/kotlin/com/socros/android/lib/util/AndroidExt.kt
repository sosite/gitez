package com.socros.android.lib.util

import android.os.Build
import android.os.Build.VERSION_CODES


fun currentApiInt() = Build.VERSION.SDK_INT

fun currentApi() = ApiVersion.fromValue(currentApiInt())


fun atLeastApi(api: Int) = currentApiInt() >= api

inline fun atLeastApi(api: Int, action: () -> Unit) {
	if (atLeastApi(api)) action()
}


fun atLeastApi(api: ApiVersion) = atLeastApi(api.versionCode)

inline fun atLeastApi(api: ApiVersion, action: () -> Unit) {
	if (atLeastApi(api)) action()
}


/**
 * Enumeration of the currently known SDK version codes.  These are the
 * values that can be found in [VERSION.SDK].  Version numbers
 * increment monotonically with each official platform release.
 */
enum class ApiVersion(val versionCode: Int) {
	/**
	 * Android 1.0 (1)
	 */
	BASE(VERSION_CODES.BASE),
	/**
	 * Android 1.0 (1)
	 */
	V1_BASE(VERSION_CODES.BASE),

	/**
	 * Android 1.1 (2)
	 */
	BASE_1_1(VERSION_CODES.BASE_1_1),
	/**
	 * Android 1.1 (2)
	 */
	V2_BASE_1_1(VERSION_CODES.BASE_1_1),

	/**
	 * Android 1.5 (3)
	 */
	CUPCAKE(VERSION_CODES.CUPCAKE),
	/**
	 * Android 1.5 (3)
	 */
	V3_CUPCAKE(VERSION_CODES.CUPCAKE),

	/**
	 * Android 1.6 (4)
	 */
	DONUT(VERSION_CODES.DONUT),
	/**
	 * Android 1.6 (4)
	 */
	V4_DONUT(VERSION_CODES.DONUT),

	/**
	 * Android 2.0 (5)
	 */
	ECLAIR(VERSION_CODES.ECLAIR),
	/**
	 * Android 2.0 (5)
	 */
	V5_ECLAIR(VERSION_CODES.ECLAIR),

	/**
	 * Android 2.0.1 (6)
	 */
	ECLAIR_0_1(VERSION_CODES.ECLAIR_0_1),
	/**
	 * Android 2.0.1 (6)
	 */
	V6_ECLAIR_0_1(VERSION_CODES.ECLAIR_0_1),

	/**
	 * Android 2.1 (7)
	 */
	ECLAIR_MR1(VERSION_CODES.ECLAIR_MR1),
	/**
	 * Android 2.1 (7)
	 */
	V7_ECLAIR_MR1(VERSION_CODES.ECLAIR_MR1),

	/**
	 * Android 2.2 (8)
	 */
	FROYO(VERSION_CODES.FROYO),
	/**
	 * Android 2.2 (8)
	 */
	V8_FROYO(VERSION_CODES.FROYO),

	/**
	 * Android 2.3 (9)
	 */
	GINGERBREAD(VERSION_CODES.GINGERBREAD),
	/**
	 * Android 2.3 (9)
	 */
	V9_GINGERBREAD(VERSION_CODES.GINGERBREAD),

	/**
	 * Android 2.3.3 (10)
	 */
	GINGERBREAD_MR1(VERSION_CODES.GINGERBREAD_MR1),
	/**
	 * Android 2.3.3 (10)
	 */
	V10_GINGERBREAD_MR1(VERSION_CODES.GINGERBREAD_MR1),

	/**
	 * Android 3.0 (11)
	 */
	HONEYCOMB(VERSION_CODES.HONEYCOMB),
	/**
	 * Android 3.0 (11)
	 */
	V11_HONEYCOMB(VERSION_CODES.HONEYCOMB),

	/**
	 * Android 3.1 (12)
	 */
	HONEYCOMB_MR1(VERSION_CODES.HONEYCOMB_MR1),
	/**
	 * Android 3.1 (12)
	 */
	V12_HONEYCOMB_MR1(VERSION_CODES.HONEYCOMB_MR1),

	/**
	 * Android 3.2 (13)
	 */
	HONEYCOMB_MR2(VERSION_CODES.HONEYCOMB_MR2),
	/**
	 * Android 3.2 (13)
	 */
	V13_HONEYCOMB_MR2(VERSION_CODES.HONEYCOMB_MR2),

	/**
	 * Android 4.0 (14)
	 */
	ICE_CREAM_SANDWICH(VERSION_CODES.ICE_CREAM_SANDWICH),
	/**
	 * Android 4.0 (14)
	 */
	V14_ICE_CREAM_SANDWICH(VERSION_CODES.ICE_CREAM_SANDWICH),

	/**
	 * Android 4.0.3 (15)
	 */
	ICE_CREAM_SANDWICH_MR1(VERSION_CODES.ICE_CREAM_SANDWICH_MR1),
	/**
	 * Android 4.0.3 (15)
	 */
	V15_ICE_CREAM_SANDWICH_MR1(VERSION_CODES.ICE_CREAM_SANDWICH_MR1),

	/**
	 * Android 4.1 (16)
	 */
	JELLY_BEAN(VERSION_CODES.JELLY_BEAN),
	/**
	 * Android 4.1 (16)
	 */
	V16_JELLY_BEAN(VERSION_CODES.JELLY_BEAN),

	/**
	 * Android 4.2 (17)
	 */
	JELLY_BEAN_MR1(VERSION_CODES.JELLY_BEAN_MR1),
	/**
	 * Android 4.2 (17)
	 */
	V17_JELLY_BEAN_MR1(VERSION_CODES.JELLY_BEAN_MR1),

	/**
	 * Android 4.3 (18)
	 */
	JELLY_BEAN_MR2(VERSION_CODES.JELLY_BEAN_MR2),
	/**
	 * Android 4.3 (18)
	 */
	V18_JELLY_BEAN_MR2(VERSION_CODES.JELLY_BEAN_MR2),

	/**
	 * Android 4.4 (19)
	 */
	KITKAT(VERSION_CODES.KITKAT),
	/**
	 * Android 4.4 (19)
	 */
	V19_KITKAT(VERSION_CODES.KITKAT),

	/**
	 * Android 4.4W (20)
	 */
	KITKAT_WATCH(VERSION_CODES.KITKAT_WATCH),
	/**
	 * Android 4.4W (20)
	 */
	V20_KITKAT_WATCH(VERSION_CODES.KITKAT_WATCH),

	/**
	 * Android 5.0 (21)
	 */
	LOLLIPOP(VERSION_CODES.LOLLIPOP),
	/**
	 * Android 5.0 (21)
	 */
	V21_LOLLIPOP(VERSION_CODES.LOLLIPOP),

	/**
	 * Android 5.1 (22)
	 */
	LOLLIPOP_MR1(VERSION_CODES.LOLLIPOP_MR1),
	/**
	 * Android 5.1 (22)
	 */
	V22_LOLLIPOP_MR1(VERSION_CODES.LOLLIPOP_MR1),

	/**
	 * Android 6.0 (23) Marshmallow
	 */
	M(VERSION_CODES.M),
	/**
	 * Android 6.0 (23)
	 */
	MARSHMALLOW(VERSION_CODES.M),
	/**
	 * Android 6.0 (23)
	 */
	V23_MARSHMALLOW(VERSION_CODES.M),

	/**
	 * Android 7.0 (24) Nougat
	 */
	N(VERSION_CODES.N),
	/**
	 * Android 7.0 (24)
	 */
	NOUGAT(VERSION_CODES.N),
	/**
	 * Android 7.0 (24)
	 */
	V24_NOUGAT(VERSION_CODES.N),

	/**
	 * Android 7.1 (25) Nougat
	 */
	N_MR1(VERSION_CODES.N_MR1),
	/**
	 * Android 7.1 (25)
	 */
	NOUGAT_MR1(VERSION_CODES.N_MR1),
	/**
	 * Android 7.1 (25)
	 */
	V25_NOUGAT_MR1(VERSION_CODES.N_MR1),

	/**
	 * Android 8.0 (26) Oreo
	 */
	O(VERSION_CODES.O),
	/**
	 * Android 8.0 (26)
	 */
	OREO(VERSION_CODES.O),
	/**
	 * Android 8.0 (26)
	 */
	V26_OREO(VERSION_CODES.O),

	/**
	 * Android 8.1 (27) Oreo
	 */
	O_MR1(VERSION_CODES.O_MR1),
	/**
	 * Android 8.1 (27)
	 */
	OREO_MR1(VERSION_CODES.O_MR1),
	/**
	 * Android 8.1 (27)
	 */
	V27_OREO_MR1(VERSION_CODES.O_MR1),

	/**
	 * Android 9.0 (28) Pie
	 */
	P(VERSION_CODES.P),
	/**
	 * Android 9.0 (28)
	 */
	PIE(VERSION_CODES.P),
	/**
	 * Android 9.0 (28)
	 */
	V28_PIE(VERSION_CODES.P);

	companion object : EnumValueCompanion<Int, ApiVersion>(
			ApiVersion.values().associateBy(ApiVersion::versionCode)
	)
}
