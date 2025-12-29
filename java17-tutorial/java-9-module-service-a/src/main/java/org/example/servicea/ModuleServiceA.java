package org.example.servicea;

import org.example.service.ModuleService;

public class ModuleServiceA implements ModuleService {

    public ModuleService getModuleService(){
        return new ModuleServiceA();
    }
}

