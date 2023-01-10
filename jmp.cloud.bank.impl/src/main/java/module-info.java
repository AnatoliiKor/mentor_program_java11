module jmp.cloud.bank.impl {
    exports jmp.cloud.bank.impl;
    requires transitive jmp.bank.api;
    provides jmp.bank.api.Bank with jmp.cloud.bank.impl.BankImpl;
}

