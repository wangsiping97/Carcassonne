package edu.cmu.cs.cs214.hw4.core;

/**
 * The enum of each segment type.
 */
public enum SegmentType {
    CITY {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == CITY || s == CITYWALL || s == CITYOFARMS;
        }
    },
    CITYWALL {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == CITY || s == CITYWALL || s == CITYOFARMS;
        }
    },
    CITYOFARMS {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == CITY || s == CITYWALL || s == CITYOFARMS;
        }
    },
    ROAD {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == ROAD || s == ROADEND;
        }
    },
    ROADEND {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == ROAD || s == ROADEND;
        }
    },
    MONASTERY {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == MONASTERY;
        }
    },
    FIELD {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == FIELD;
        }
    },
    NONE {
        /**
         * Checks whether two segment types match with each other.
         * @param s another segment type.
         * @return whether the two match with each other.
         */
        public boolean matches(SegmentType s) {
            return s == NONE;
        }
    };

    /**
     * Checks whether two segment types match with each other.
     * @param s another segment type.
     * @return whether the two match with each other.
     */
    public abstract boolean matches(SegmentType s);
}
