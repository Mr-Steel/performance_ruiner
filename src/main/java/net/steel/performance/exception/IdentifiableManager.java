package net.steel.performance.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is for demo purposes only!
 * It just a show case and therefore has no real implementation value.
 * Nevertheless, it contains the important part, that has caused the performance issues.
 */
public class IdentifiableManager {

    private static IdentifiableManager identifiableManager = new IdentifiableManager();

    private Map<Long, Object> identifiableObjectMap = new HashMap<>();

    private IdentifiableManager() {
        for (long i = 0; i < ExceptionDrivenBenchmark.LOOP_SIZE; i++) {
            if (i%2 == 0) {
                identifiableObjectMap.put(i, new Object());
            }
        }
    }

    public static IdentifiableManager current() {
        return identifiableManager;
    };

    public Object getIdentifiable(long objectID) throws IdentifiableNotFoundException {
        Object result = identifiableObjectMap.get(objectID);
        if (result == null) {
            throw new IdentifiableNotFoundException(objectID + " not found!");
        }
        return result;
    }


    /**
     * Added method to fix missing functionality and to prevent
     * exception driven logic.
     *
     * @param objectID
     * @return true if the map contains the given objectID, false otherwise
     */
    public boolean containsIdentifiable(long objectID) {
        if (identifiableObjectMap.containsKey(objectID)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsIdentifiable_2(Long objectID) {
        if (identifiableObjectMap.containsKey(objectID)) {
            return true;
        } else {
            return false;
        }
    }

}
