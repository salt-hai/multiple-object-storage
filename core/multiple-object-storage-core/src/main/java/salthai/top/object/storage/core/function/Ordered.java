package salthai.top.object.storage.core.function;

/**
 * 排序接口 实际的 {@link #getOrder()}可以解释为优先级，第一个对象 (具有最低顺序值) 具有最高优先级。
 *
 * @see OrderComparator 该类子类的比较器
 * @author Kuang HaiBo 2023/11/21 14:48
 */
public interface Ordered {

	/**
	 * Useful constant for the highest precedence value.
	 * @see java.lang.Integer#MIN_VALUE
	 */
	int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

	/**
	 * Useful constant for the lowest precedence value.
	 * @see java.lang.Integer#MAX_VALUE
	 */
	int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

	/**
	 * 获取此对象的order值。 较高的值被解释为较低的优先级。因此，具有最低值的对象具有最高优先级 (有点类似于Servlet启动时加载的值)。
	 * 相同的顺序值将导致受影响对象的任意排序位置
	 * <p>
	 * @return the order value
	 * @see #HIGHEST_PRECEDENCE
	 * @see #LOWEST_PRECEDENCE
	 */
	int getOrder();

}
