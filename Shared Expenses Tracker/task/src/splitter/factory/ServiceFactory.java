package splitter.factory;

import splitter.entries.Entry;
import splitter.service.BalanceService;
import splitter.service.BorrowService;
import splitter.service.RepayService;
import splitter.service.Service;

public class ServiceFactory {
    public static Service get(String service) {
        Entry entry = Entry.valueOf(service.toUpperCase());
        return switch (entry) {
            case BALANCE -> BalanceService.getInstance();
            case REPAY -> RepayService.getInstance();
            case BORROW -> BorrowService.getInstance();
            default -> throw new IllegalArgumentException("Service not found!");
        };
    }
}
