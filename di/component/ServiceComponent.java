

package com.lionroarsrk.scapp.di.component;



import com.lionroarsrk.scapp.di.PerService;
import com.lionroarsrk.scapp.di.module.ServiceModule;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {


}
