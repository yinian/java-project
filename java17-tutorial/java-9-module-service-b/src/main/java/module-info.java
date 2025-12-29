module org.example.serviceb {
    requires transitive org.example.service;
    provides org.example.service.ModuleService with org.example.serviceb.ModuleServiceB;
    exports org.example.serviceb;
}
