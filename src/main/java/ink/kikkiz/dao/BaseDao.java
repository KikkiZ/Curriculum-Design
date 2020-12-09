package ink.kikkiz.dao;

import java.util.Map;
import java.util.Set;


/**
 * @author KikkiZ
 * 定义公共操作接口标准，包含基本的增、删、改、查
 */
public interface BaseDao<K, V> {
    /**
     * 实现数据的增加操作
     *
     * @param value 需要添加到数据库中的对象
     * @return 数据成功添加则返回true，否则返回false
     */
    boolean creat(V value);

    /**
     * 实现数据的修改操作
     *
     * @param value 包含了修改数据的信息，该对象中需要提供id
     * @return 数据成功修改则返回true，否则返回false
     */
    boolean update(V value);

    /**
     * 执行移除操作
     *
     * @param key 指定需要移除数据的id
     * @return 数据成功移除则返回true，否则返回false
     */
    boolean remove(K key);

    /**
     * 执行批量移除操作
     *
     * @param keys 指定一批需要移除数据的id
     * @return 数据成功移除则返回true，否则返回false
     */
    boolean removeBatch(Set<K> keys);

    /**
     * 查找指定id的数据信息
     *
     * @param key 需要查找的id
     * @return 根据查询到的信息，将其封装为具体实体类
     */
    V findById(K key);

    /**
     * 查找所有的数据信息
     *
     * @return 将所有查找到的数据信息封装为实体类，然后返回map集合
     */
    Map<K, V> findAll();
}
