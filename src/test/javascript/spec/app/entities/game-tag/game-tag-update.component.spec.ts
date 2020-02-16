/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GameTagUpdateComponent from '@/entities/game-tag/game-tag-update.vue';
import GameTagClass from '@/entities/game-tag/game-tag-update.component';
import GameTagService from '@/entities/game-tag/game-tag.service';

import GameService from '@/entities/game/game.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('GameTag Management Update Component', () => {
    let wrapper: Wrapper<GameTagClass>;
    let comp: GameTagClass;
    let gameTagServiceStub: SinonStubbedInstance<GameTagService>;

    beforeEach(() => {
      gameTagServiceStub = sinon.createStubInstance<GameTagService>(GameTagService);

      wrapper = shallowMount<GameTagClass>(GameTagUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          gameTagService: () => gameTagServiceStub,

          gameService: () => new GameService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.gameTag = entity;
        gameTagServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gameTagServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.gameTag = entity;
        gameTagServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gameTagServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
