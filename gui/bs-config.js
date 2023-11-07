const webpack = require("webpack");
const { HotModuleReplacementPlugin } = webpack;
const webpackDevMiddleware = require("webpack-dev-middleware");
const webpackHotMiddleware = require("webpack-hot-middleware");
const { plugin, entry, output } = require("webpack-partial");
const flow = require("lodash/flow");
const path = require("path");

const webpackConfig = require("./browsersync/webpack.config");

const startPath = "/";

const publicPath = path.join(startPath, webpackConfig.output.publicPath);

const bundler = webpack(
    flow([
        output({ publicPath }),
        plugin(new HotModuleReplacementPlugin()),
        entry((previous) => ["webpack-hot-middleware/client", "webpack/hot/only-dev-server", ...previous]),
    ])(webpackConfig),
);

module.exports = {
    open: false,
    logLevel: "debug",
    startPath,
    proxy: {
        target: "localhost:8090",
        middleware: [webpackDevMiddleware(bundler, { publicPath }), webpackHotMiddleware(bundler)],
    },
    serveStatic: [],
};
