const CompressionPlugin = require('compression-webpack-plugin');

module.exports = {
    webpack: {
        plugins: [
            new CompressionPlugin({
                filename: '[path].gz[query]', // 압축된 파일 이름
                algorithm: 'gzip', // Gzip 알고리즘 사용
                test: /\.(js|css|html|svg)$/, // 압축할 파일 형식
                threshold: 10240, // 압축할 최소 파일 크기 (10KB)
                minRatio: 0.8, // 최소 압축 비율
            }),
        ],
    },
};