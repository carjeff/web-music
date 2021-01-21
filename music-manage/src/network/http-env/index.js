import prod from './production.env'
import dev from './dev.env'

const env = process.env.VUE_APP_CURRENT_ENV

export default env === 'dev' ? dev : prod
