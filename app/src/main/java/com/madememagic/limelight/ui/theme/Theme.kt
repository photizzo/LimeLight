package com.madememagic.limelight.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Immutable
data class ExtendedColorScheme(
    val maroon: ColorFamily,
    val brown: ColorFamily,
    val mustard: ColorFamily,
    val green: ColorFamily,
    val teal: ColorFamily,
    val olive: ColorFamily,
    val rust: ColorFamily,
    val dustyRose: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
)

val extendedLight = ExtendedColorScheme(
    maroon = ColorFamily(
        maroonLight,
        onMaroonLight,
        maroonContainerLight,
        onMaroonContainerLight,
    ),
    brown = ColorFamily(
        brownLight,
        onBrownLight,
        brownContainerLight,
        onBrownContainerLight,
    ),
    mustard = ColorFamily(
        mustardLight,
        onMustardLight,
        mustardContainerLight,
        onMustardContainerLight,
    ),
    green = ColorFamily(
        greenLight,
        onGreenLight,
        greenContainerLight,
        onGreenContainerLight,
    ),
    teal = ColorFamily(
        tealLight,
        onTealLight,
        tealContainerLight,
        onTealContainerLight,
    ),
    olive = ColorFamily(
        oliveLight,
        onOliveLight,
        oliveContainerLight,
        onOliveContainerLight,
    ),
    rust = ColorFamily(
        rustLight,
        onRustLight,
        rustContainerLight,
        onRustContainerLight,
    ),
    dustyRose = ColorFamily(
        dustyRoseLight,
        onDustyRoseLight,
        dustyRoseContainerLight,
        onDustyRoseContainerLight,
    ),
)

val extendedDark = ExtendedColorScheme(
    maroon = ColorFamily(
        maroonDark,
        onMaroonDark,
        maroonContainerDark,
        onMaroonContainerDark,
    ),
    brown = ColorFamily(
        brownDark,
        onBrownDark,
        brownContainerDark,
        onBrownContainerDark,
    ),
    mustard = ColorFamily(
        mustardDark,
        onMustardDark,
        mustardContainerDark,
        onMustardContainerDark,
    ),
    green = ColorFamily(
        greenDark,
        onGreenDark,
        greenContainerDark,
        onGreenContainerDark,
    ),
    teal = ColorFamily(
        tealDark,
        onTealDark,
        tealContainerDark,
        onTealContainerDark,
    ),
    olive = ColorFamily(
        oliveDark,
        onOliveDark,
        oliveContainerDark,
        onOliveContainerDark,
    ),
    rust = ColorFamily(
        rustDark,
        onRustDark,
        rustContainerDark,
        onRustContainerDark,
    ),
    dustyRose = ColorFamily(
        dustyRoseDark,
        onDustyRoseDark,
        dustyRoseContainerDark,
        onDustyRoseContainerDark,
    ),
)

val extendedLightMediumContrast = ExtendedColorScheme(
    maroon = ColorFamily(
        maroonLightMediumContrast,
        onMaroonLightMediumContrast,
        maroonContainerLightMediumContrast,
        onMaroonContainerLightMediumContrast,
    ),
    brown = ColorFamily(
        brownLightMediumContrast,
        onBrownLightMediumContrast,
        brownContainerLightMediumContrast,
        onBrownContainerLightMediumContrast,
    ),
    mustard = ColorFamily(
        mustardLightMediumContrast,
        onMustardLightMediumContrast,
        mustardContainerLightMediumContrast,
        onMustardContainerLightMediumContrast,
    ),
    green = ColorFamily(
        greenLightMediumContrast,
        onGreenLightMediumContrast,
        greenContainerLightMediumContrast,
        onGreenContainerLightMediumContrast,
    ),
    teal = ColorFamily(
        tealLightMediumContrast,
        onTealLightMediumContrast,
        tealContainerLightMediumContrast,
        onTealContainerLightMediumContrast,
    ),
    olive = ColorFamily(
        oliveLightMediumContrast,
        onOliveLightMediumContrast,
        oliveContainerLightMediumContrast,
        onOliveContainerLightMediumContrast,
    ),
    rust = ColorFamily(
        rustLightMediumContrast,
        onRustLightMediumContrast,
        rustContainerLightMediumContrast,
        onRustContainerLightMediumContrast,
    ),
    dustyRose = ColorFamily(
        dustyRoseLightMediumContrast,
        onDustyRoseLightMediumContrast,
        dustyRoseContainerLightMediumContrast,
        onDustyRoseContainerLightMediumContrast,
    ),
)

val extendedLightHighContrast = ExtendedColorScheme(
    maroon = ColorFamily(
        maroonLightHighContrast,
        onMaroonLightHighContrast,
        maroonContainerLightHighContrast,
        onMaroonContainerLightHighContrast,
    ),
    brown = ColorFamily(
        brownLightHighContrast,
        onBrownLightHighContrast,
        brownContainerLightHighContrast,
        onBrownContainerLightHighContrast,
    ),
    mustard = ColorFamily(
        mustardLightHighContrast,
        onMustardLightHighContrast,
        mustardContainerLightHighContrast,
        onMustardContainerLightHighContrast,
    ),
    green = ColorFamily(
        greenLightHighContrast,
        onGreenLightHighContrast,
        greenContainerLightHighContrast,
        onGreenContainerLightHighContrast,
    ),
    teal = ColorFamily(
        tealLightHighContrast,
        onTealLightHighContrast,
        tealContainerLightHighContrast,
        onTealContainerLightHighContrast,
    ),
    olive = ColorFamily(
        oliveLightHighContrast,
        onOliveLightHighContrast,
        oliveContainerLightHighContrast,
        onOliveContainerLightHighContrast,
    ),
    rust = ColorFamily(
        rustLightHighContrast,
        onRustLightHighContrast,
        rustContainerLightHighContrast,
        onRustContainerLightHighContrast,
    ),
    dustyRose = ColorFamily(
        dustyRoseLightHighContrast,
        onDustyRoseLightHighContrast,
        dustyRoseContainerLightHighContrast,
        onDustyRoseContainerLightHighContrast,
    ),
)

val extendedDarkMediumContrast = ExtendedColorScheme(
    maroon = ColorFamily(
        maroonDarkMediumContrast,
        onMaroonDarkMediumContrast,
        maroonContainerDarkMediumContrast,
        onMaroonContainerDarkMediumContrast,
    ),
    brown = ColorFamily(
        brownDarkMediumContrast,
        onBrownDarkMediumContrast,
        brownContainerDarkMediumContrast,
        onBrownContainerDarkMediumContrast,
    ),
    mustard = ColorFamily(
        mustardDarkMediumContrast,
        onMustardDarkMediumContrast,
        mustardContainerDarkMediumContrast,
        onMustardContainerDarkMediumContrast,
    ),
    green = ColorFamily(
        greenDarkMediumContrast,
        onGreenDarkMediumContrast,
        greenContainerDarkMediumContrast,
        onGreenContainerDarkMediumContrast,
    ),
    teal = ColorFamily(
        tealDarkMediumContrast,
        onTealDarkMediumContrast,
        tealContainerDarkMediumContrast,
        onTealContainerDarkMediumContrast,
    ),
    olive = ColorFamily(
        oliveDarkMediumContrast,
        onOliveDarkMediumContrast,
        oliveContainerDarkMediumContrast,
        onOliveContainerDarkMediumContrast,
    ),
    rust = ColorFamily(
        rustDarkMediumContrast,
        onRustDarkMediumContrast,
        rustContainerDarkMediumContrast,
        onRustContainerDarkMediumContrast,
    ),
    dustyRose = ColorFamily(
        dustyRoseDarkMediumContrast,
        onDustyRoseDarkMediumContrast,
        dustyRoseContainerDarkMediumContrast,
        onDustyRoseContainerDarkMediumContrast,
    ),
)

val extendedDarkHighContrast = ExtendedColorScheme(
    maroon = ColorFamily(
        maroonDarkHighContrast,
        onMaroonDarkHighContrast,
        maroonContainerDarkHighContrast,
        onMaroonContainerDarkHighContrast,
    ),
    brown = ColorFamily(
        brownDarkHighContrast,
        onBrownDarkHighContrast,
        brownContainerDarkHighContrast,
        onBrownContainerDarkHighContrast,
    ),
    mustard = ColorFamily(
        mustardDarkHighContrast,
        onMustardDarkHighContrast,
        mustardContainerDarkHighContrast,
        onMustardContainerDarkHighContrast,
    ),
    green = ColorFamily(
        greenDarkHighContrast,
        onGreenDarkHighContrast,
        greenContainerDarkHighContrast,
        onGreenContainerDarkHighContrast,
    ),
    teal = ColorFamily(
        tealDarkHighContrast,
        onTealDarkHighContrast,
        tealContainerDarkHighContrast,
        onTealContainerDarkHighContrast,
    ),
    olive = ColorFamily(
        oliveDarkHighContrast,
        onOliveDarkHighContrast,
        oliveContainerDarkHighContrast,
        onOliveContainerDarkHighContrast,
    ),
    rust = ColorFamily(
        rustDarkHighContrast,
        onRustDarkHighContrast,
        rustContainerDarkHighContrast,
        onRustContainerDarkHighContrast,
    ),
    dustyRose = ColorFamily(
        dustyRoseDarkHighContrast,
        onDustyRoseDarkHighContrast,
        dustyRoseContainerDarkHighContrast,
        onDustyRoseContainerDarkHighContrast,
    ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

