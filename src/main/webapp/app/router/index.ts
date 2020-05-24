import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
import { Authority } from '@/shared/security/authority';
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const GameSystem = () => import('../entities/game-system/game-system.vue');
// prettier-ignore
const GameSystemUpdate = () => import('../entities/game-system/game-system-update.vue');
// prettier-ignore
const GameSystemDetails = () => import('../entities/game-system/game-system-details.vue');
// prettier-ignore
const GameTag = () => import('../entities/game-tag/game-tag.vue');
// prettier-ignore
const GameTagUpdate = () => import('../entities/game-tag/game-tag-update.vue');
// prettier-ignore
const GameTagDetails = () => import('../entities/game-tag/game-tag-details.vue');
// prettier-ignore
const Character = () => import('../entities/character/character.vue');
// prettier-ignore
const CharacterUpdate = () => import('../entities/character/character-update.vue');
// prettier-ignore
const CharacterDetails = () => import('../entities/character/character-details.vue');
// prettier-ignore
const Game = () => import('../entities/game/game.vue');
// prettier-ignore
const GameUpdate = () => import('../entities/game/game-update.vue');
// prettier-ignore
const GameDetails = () => import('../entities/game/game-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      redirect: '/game'
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/account/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/account/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/account/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: [Authority.ADMIN] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: [Authority.ADMIN] }
    }
    ,
    {
      path: '/game-system',
      name: 'GameSystem',
      component: GameSystem,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game-system/new',
      name: 'GameSystemCreate',
      component: GameSystemUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game-system/:gameSystemId/edit',
      name: 'GameSystemEdit',
      component: GameSystemUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game-system/:gameSystemId/view',
      name: 'GameSystemView',
      component: GameSystemDetails,
      meta: { authorities: [Authority.USER] }
    }
    ,
    {
      path: '/game-tag',
      name: 'GameTag',
      component: GameTag,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game-tag/new',
      name: 'GameTagCreate',
      component: GameTagUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game-tag/:gameTagId/edit',
      name: 'GameTagEdit',
      component: GameTagUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game-tag/:gameTagId/view',
      name: 'GameTagView',
      component: GameTagDetails,
      meta: { authorities: [Authority.USER] }
    }
    ,
    {
      path: '/character',
      name: 'Character',
      component: Character,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/character/new',
      name: 'CharacterCreate',
      component: CharacterUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/character/:characterId/edit',
      name: 'CharacterEdit',
      component: CharacterUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/character/:characterId/view',
      name: 'CharacterView',
      component: CharacterDetails,
      meta: { authorities: [Authority.USER] }
    }
    ,
    {
      path: '/game',
      name: 'Game',
      component: Game,
    },
    {
      path: '/game/new',
      name: 'GameCreate',
      component: GameUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game/:gameId/edit',
      name: 'GameEdit',
      component: GameUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: '/game/:gameId/view',
      name: 'GameView',
      component: GameDetails,
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
});
