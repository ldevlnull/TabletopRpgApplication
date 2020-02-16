/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GameComponent from '@/entities/game/game.vue';
import GameClass from '@/entities/game/game.component';
import GameService from '@/entities/game/game.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {}
  }
};

describe('Component Tests', () => {
  describe('Game Management Component', () => {
    let wrapper: Wrapper<GameClass>;
    let comp: GameClass;
    let gameServiceStub: SinonStubbedInstance<GameService>;

    beforeEach(() => {
      gameServiceStub = sinon.createStubInstance<GameService>(GameService);
      gameServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GameClass>(GameComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          gameService: () => gameServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      gameServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGames();
      await comp.$nextTick();

      // THEN
      expect(gameServiceStub.retrieve.called).toBeTruthy();
      expect(comp.games[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      gameServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGame();
      await comp.$nextTick();

      // THEN
      expect(gameServiceStub.delete.called).toBeTruthy();
      expect(gameServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
