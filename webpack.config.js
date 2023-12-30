// Imports
const path = require('path');

// Variables
const outputDirectory = './src/main/resources/static/js';


module.exports = {
    mode: 'development',
    entry: [
        'babel-polyfill',
        /**
         * The list for React components starts here. Please keep these in
         * alphabetical order.
         */
        './src/main/resources/static/js/void/main.js',
//        './src/main/resources/static/js/components/forms/loginform.js',
//        './src/main/resources/static/js/components/nav/navigation.js'
    ],
    output: {
        path: path.join(__dirname, outputDirectory),
        filename: 'main.js'
    },
    module: {
        rules: [{
            test: /\.js$|jsx/,
            exclude: /node_modules/,
            use: {
                loader: 'babel-loader'
            }
        },
        {
            test: /\.css$/i,
            use: [
                "style-loader",
                    {
                        loader: "css-loader",
                        options: {
                            importLoaders: 1,
                        },
                    }
                ]
        }]
    },
    devServer: {
        port: 3000,
        open: true,
        proxy: {
            '/': 'http://localhost:8080'
        }
    }
};