/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CharacterUpdateComponent from '@/entities/character/character-update.vue';
import CharacterClass from '@/entities/character/character-update.component';
import CharacterService from '@/entities/character/character.service';

import UserService from '@/admin/user-management/user-management.service';

import GameSystemService from '@/entities/game-system/game-system.service';

import GameService from '@/entities/game/game.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Character Management Update Component', () => {
    let wrapper: Wrapper<CharacterClass>;
    let comp: CharacterClass;
    let characterServiceStub: SinonStubbedInstance<CharacterService>;

    beforeEach(() => {
      characterServiceStub = sinon.createStubInstance<CharacterService>(CharacterService);

      wrapper = shallowMount<CharacterClass>(CharacterUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          characterService: () => characterServiceStub,

          userService: () => new UserService(),

          gameSystemService: () => new GameSystemService(),

          gameService: () => new GameService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.character = entity;
        characterServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(characterServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.character = entity;
        characterServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(characterServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
