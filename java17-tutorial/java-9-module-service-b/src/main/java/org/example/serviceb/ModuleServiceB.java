package org.example.serviceb;

import org.example.service.ModuleService;

public class ModuleServiceB implements ModuleService {

    public ModuleService getModuleService(){
        return new ModuleServiceB();
    }
}
