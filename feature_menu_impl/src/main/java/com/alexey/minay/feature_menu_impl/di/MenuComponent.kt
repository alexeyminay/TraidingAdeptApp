package com.alexey.minay.feature_menu_impl.di

import com.alexey.minay.core_dagger2.NeedInitializeException
import com.alexey.minay.core_dagger2.ViewModelProviderFactory
import dagger.Component

@Component(
    modules = [MenuBindings::class],
    dependencies = [MenuDependencies::class]
)
interface MenuComponent {

    val viewModelProviderFactory: ViewModelProviderFactory

    companion object {

        private var mDependencies: MenuDependencies? = null

        fun init(dependencies: MenuDependencies) {
            this.mDependencies = dependencies
        }

        internal fun create() = DaggerMenuComponent
            .builder()
            .menuDependencies(
                mDependencies ?: throw NeedInitializeException()
            ).build()

    }

}