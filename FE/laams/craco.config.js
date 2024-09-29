const CompressionPlugin = require('compression-webpack-plugin');

module.exports = {
    webpack: {
        plugins: [
            new CompressionPlugin({
                filename: '[path][name].gz[query]', // 파일 경로와 이름을 포함
                algorithm: 'gzip',
                test: /\.(js|css|html|svg)$/,
                threshold: 10240, // 압축할 최소 파일 크기 (10KB)
                minRatio: 0.8, // 최소 압축 비율
            }),
        ],
    },
};
