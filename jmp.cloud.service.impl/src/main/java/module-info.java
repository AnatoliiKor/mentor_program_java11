module jmp.cloud.service.impl {
    exports jmp.cloud.service.impl;
    requires transitive jmp.service.api;
    provides jmp.service.api.Service with jmp.cloud.service.impl.ServiceImpl;
}

