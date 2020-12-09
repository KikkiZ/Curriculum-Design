package ink.kikkiz.gui.factory;

/**
 * @author KikkiZ
 */
public interface ControlFactory<T> {
    /**
     * 获取指定产品
     *
     * @return 特定产品
     */
    T newProduct();
}
