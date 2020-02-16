/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GameSystemDetailComponent from '@/entities/game-system/game-system-details.vue';
import GameSystemClass from '@/entities/game-system/game-system-details.component';
import GameSystemService from '@/entities/game-system/game-system.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GameSystem Management Detail Component', () => {
    let wrapper: Wrapper<GameSystemClass>;
    let comp: GameSystemClass;
    let gameSystemServiceStub: SinonStubbedInstance<GameSystemService>;

    beforeEach(() => {
      gameSystemServiceStub = sinon.createStubInstance<GameSystemService>(GameSystemService);

      wrapper = shallowMount<GameSystemClass>(GameSystemDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { gameSystemService: () => gameSystemServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGameSystem = { id: 123 };
        gameSystemServiceStub.find.resolves(foundGameSystem);

        // WHEN
        comp.retrieveGameSystem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.gameSystem).toBe(foundGameSystem);
      });
    });
  });
});
