const path = require("path");
const ROOT = process.cwd();
const PKG = require(path.resolve(ROOT, "./package.json"));

const config = require("../webpack.config");
const initConfig = config(null, { mode: "development" });

module.exports = {
    ...initConfig,
    mode: "development",
    output: {
        ...initConfig.output,
        filename: PKG.name + ".debug.js",
        publicPath: "/js",
    },
};
