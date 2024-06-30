package salthai.top.object.storage.core.function;

import java.util.function.Supplier;

/**
 * copy from spring
 *
 * @author Kuang HaiBo 2023/10/27 15:35
 */
public abstract class SupplierUtils {

	/**
	 * Resolve the given {@code Supplier}, getting its result or immediately returning
	 * {@code null} if the supplier itself was {@code null}.
	 * @param supplier the supplier to resolve
	 * @return the supplier's result, or {@code null} if none
	 */
	public static <T> T resolve(Supplier<T> supplier) {
		return (supplier != null ? supplier.get() : null);
	}

}
