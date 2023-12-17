package co.aospa.android.customization.module

import android.app.Activity

import android.content.Context

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.android.wallpaper.dispatchers.BackgroundDispatcher
import com.android.wallpaper.dispatchers.MainDispatcher
import com.android.wallpaper.module.CustomizationSections
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

import com.android.customization.picker.clock.ui.view.ClockViewFactory
import com.android.customization.picker.clock.ui.viewmodel.ClockCarouselViewModel
import com.android.customization.picker.notifications.ui.viewmodel.NotificationSectionViewModel

import com.android.customization.model.mode.DarkModeSnapshotRestorer
import com.android.customization.model.themedicon.domain.interactor.ThemedIconInteractor
import com.android.customization.model.themedicon.domain.interactor.ThemedIconSnapshotRestorer
import com.android.customization.module.ThemePickerInjector

@Singleton
open class AospaThemePickerInjector @Inject constructor(
    @MainDispatcher mainScope: CoroutineScope,
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    @BackgroundDispatcher bgDispatcher: CoroutineDispatcher,
) : ThemePickerInjector(
    mainScope,
    mainDispatcher,
    bgDispatcher,
) {

    private var customizationSections: CustomizationSections? = null

    override fun getCustomizationSections(activity: ComponentActivity): CustomizationSections {
    val wallpaperColorsViewModel = getWallpaperColorsViewModel()
        return customizationSections
            ?: AospaCustomizationSections(
                    getColorPickerViewModelFactory(
                        context = activity,
                    wallpaperColorsViewModel = wallpaperColorsViewModel,
                    ),
                    getKeyguardQuickAffordancePickerInteractor(activity),
                    getKeyguardQuickAffordancePickerViewModelFactory(activity),
                    getNotificationSectionViewModelFactory(activity),
                    getFlags(),
                    getClockCarouselViewModelFactory(
                        getClockPickerInteractor(activity.applicationContext),
                    ),
                    getClockViewFactory(activity),
                    getDarkModeSnapshotRestorer(activity),
                    getThemedIconSnapshotRestorer(activity),
                    getThemedIconInteractor(),
                    getColorPickerInteractor(activity, wallpaperColorsViewModel),
                )
                .also { customizationSections = it }
    }
}
