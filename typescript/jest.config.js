module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'node',
    roots: [
         '<rootDir>/src/' // pointing to tests directory
    ],
    modulePathIgnorePatterns: [
        "<rootDir>/build/"
    ],
    testMatch: [
        "**/__tests__/**/*.+(ts|tsx|js)",
        "**/?(*.)+(spec|test).+(ts|tsx|js)"
    ],
    transform: {
        "^.+\\.(ts|tsx)$": "ts-jest"
    },
};
