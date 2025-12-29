
module org.example.servicea {
    requires org.example.service;
    provides org.example.service.ModuleService with org.example.servicea.ModuleServiceA;
    exports org.example.servicea;

}
