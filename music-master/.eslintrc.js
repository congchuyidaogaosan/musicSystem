module.exports = {
  root: false,
  env: {
    node: true
  },
  extends: [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'semi':0,
    'space-before-function-paren': 0,
    'indent': 'off',
    'eqeqeq': 'off',
    'prefer-const': 'off',
    'vue/no-parsing-error': [2, { 'x-invalid-end-tag': false }]
  }
}
