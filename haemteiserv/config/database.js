module.exports = ({ env }) => ({
  connection: {
    client: env('DATABASE_CLIENT', 'mysql'),
    connection: {
      host: env('DATABASE_HOST', '172.16.79.128'),
      port: env.int('DATABASE_PORT', 3306),
      database: env('DATABASE_NAME', 'progmobmod5'),
      user: env('DATABASE_USERNAME', 'entah'),
      password: env('DATABASE_PASSWORD', 'entah'),
      ssl: { "rejectUnauthorized": false },
    },
  },
});

// Admin password nfxcw75ikNtf84