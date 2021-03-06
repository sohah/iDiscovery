// The three files
//   Option.java
//   Options.java
//   Unpublicized.java
// together comprise the implementation of command-line processing.

package daikon.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in conjunction with the @Option annotation to signal that
 * the option should not be included in the usage message.
 * @see plume.Option
 * @see plume.Options
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unpublicized {

}
