package org.example.controller;

import org.example.service.ModuleService;

import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;
public class ModuleController {


    public static void main(String[] args) {
        List<ModuleService> moduleServices = ServiceLoader
                .load(ModuleService.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
        System.out.printf("{}", moduleServices);
    }
}

