module.exports = ({ env }) => ({
  auth: {
    secret: env('ADMIN_JWT_SECRET', 'c8f3bbeb44e9fe7afb1ff6580a60f349'),
  },
});
