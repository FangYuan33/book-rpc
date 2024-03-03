package book.rpc.core.service;

import book.rpc.interfaces.DataService;

/**
 * 测试服务实现
 *
 * @author FangYuan
 * @since 2024-03-03 17:28:25
 */
public class DataServiceImpl implements DataService {
    @Override
    public String test(String data) {
        return "Hello, " + data;
    }
}
