module jmp.application {
    requires jmp.dto;
    requires jmp.bank.api;
    requires jmp.service.api;
    uses jmp.bank.api.Bank;
    uses jmp.service.api.Service;
}

