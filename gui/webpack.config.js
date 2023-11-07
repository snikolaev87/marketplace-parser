const path = require("path");
const webpack = require("webpack");
const TerserPlugin = require("terser-webpack-plugin");
const CssMinimizerPlugin = require("css-minimizer-webpack-plugin");
const ROOT = process.cwd();
const PKG = require(path.resolve(ROOT, "./package.json"));

module.exports = (_, { mode }) => {
    const isDevelopment = mode === "development";

    let webpackConfig = {
        entry: "./src/main/js/app.js",
        output: {
            path: path.resolve(ROOT, "src/main/resources/js/"),
            library: PKG.name,
            libraryTarget: "umd",
            filename: PKG.name + (isDevelopment ? ".debug.js" : ".js"),
        },
        devtool: isDevelopment ? "eval-cheap-module-source-map" : false,
        module: {
            rules: [
                {
                    test: /\.(js|jsx)$/,
                    use: {
                        loader: "babel-loader",
                    },
                    exclude: [/(node_modules)/]
                },
                // Stylesheet settings
                {
                    test: /\.css$/,
                    use: [
                        'style-loader',
                        'css-loader'
                    ]
                },
            ],
        },
        resolve: {
            extensions: [".js", ".jsx", ".css", ".json"],
            symlinks: false,
            cacheWithContext: false,
        },
    };

    if (!isDevelopment) {
        webpackConfig.optimization = {
            minimize: true,
            minimizer: [new TerserPlugin(), new CssMinimizerPlugin()]  
        }
    }

    return webpackConfig;
};
