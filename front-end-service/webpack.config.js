
var path = require('path');

  module.exports = {
      webpack: (config, { isServer }) => {
              config.node = {
                  net: 'empty',
                  tls: 'empty'
              };

          return config;
      }
  }


