package edu.cmu.cs.cs214.hw4.core;

/**
 * The enum type of directions.
 */
public enum Direction {
    UP {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return DOWN;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return LEFT;
        }
    },
    DOWN {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return UP;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return RIGHT;
        }
    },
    LEFT {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return RIGHT;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return DOWN;
        }
    },
    RIGHT {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return LEFT;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return UP;
        }
    },
    UPLEFT {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return DOWNRIGHT;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return DOWNLEFT;
        }
    },
    DOWNRIGHT {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return UPLEFT;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return UPRIGHT;
        }
    },
    DOWNLEFT {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return UPRIGHT;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return DOWNRIGHT;
        }
    },
    UPRIGHT {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return DOWNLEFT;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return UPLEFT;
        }
    },
    CENTER {
        /**
         * Gets the opposite direction.
         * (up-down, left-right, upright-downleft, upleft, downright, center-center)
         * @return the opposite direction.
         */
        public Direction getOppo() {
            return CENTER;
        }
        /**
         * Gets the direction after rotating 90 degrees clockwise.
         * @return the desired direction.
         */
        public Direction getRotate90() {
            return CENTER;
        }
    };

    /**
     * Gets the opposite direction.
     * (up-down, left-right, upright-downleft, upleft, downright, center-center)
     * @return the opposite direction.
     */
    public abstract Direction getOppo();

    /**
     * Gets the direction after rotating 90 degrees clockwise.
     * @return the desired direction.
     */
    public abstract Direction getRotate90();
}
