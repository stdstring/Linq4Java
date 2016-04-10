package Linq4Java.IterableExtensionImpl;

/**
 *
 * @author std_string
 */
enum IteratorState {
    // iterator in unknown state (after obtaining next element)
    UNKNOWN,
    // iterator in active state (next element exists)
    ACTIVE,
    // iterator in final state (next element doesn't exist)
    FINISHED
}
