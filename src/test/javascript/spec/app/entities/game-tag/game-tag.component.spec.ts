/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GameTagComponent from '@/entities/game-tag/game-tag.vue';
import GameTagClass from '@/entities/game-tag/game-tag.component';
import GameTagService from '@/entities/game-tag/game-tag.service';

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
  describe('GameTag Management Component', () => {
    let wrapper: Wrapper<GameTagClass>;
    let comp: GameTagClass;
    let gameTagServiceStub: SinonStubbedInstance<GameTagService>;

    beforeEach(() => {
      gameTagServiceStub = sinon.createStubInstance<GameTagService>(GameTagService);
      gameTagServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GameTagClass>(GameTagComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          gameTagService: () => gameTagServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      gameTagServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGameTags();
      await comp.$nextTick();

      // THEN
      expect(gameTagServiceStub.retrieve.called).toBeTruthy();
      expect(comp.gameTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      gameTagServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGameTag();
      await comp.$nextTick();

      // THEN
      expect(gameTagServiceStub.delete.called).toBeTruthy();
      expect(gameTagServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
