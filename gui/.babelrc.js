module.exports = (api) => {
    const plugins = [
        "@babel/plugin-proposal-class-properties",
        "@babel/plugin-proposal-object-rest-spread",
        "@babel/plugin-syntax-dynamic-import",
        [
            "@babel/plugin-transform-runtime",
            {
                regenerator: true,
            },
        ],
        [
            "babel-plugin-transform-imports",
            {
                "react-virtualized": {
                    transform: "react-virtualized/dist/es/${member}",
                    preventFullImport: true,
                },
            },
        ],
    ];

    if (process.env.NODE_ENV === "development") {
        plugins.unshift("react-hot-loader/babel");
    }

    let presetEnvOptions = {};

    if (!api.env("test")) {
        presetEnvOptions = {
            modules: false,
            useBuiltIns: "usage",
            corejs: 3,
        };
    }

    return {
        targets: ">1%, ie 11",
        presets: [
            ["@babel/preset-env", presetEnvOptions],
            [
                "@babel/preset-react",
                {
                    runtime: "automatic",
                },
            ], 
        ],
        plugins,
    };
};
