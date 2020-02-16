/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GameDetailComponent from '@/entities/game/game-details.vue';
import GameClass from '@/entities/game/game-details.component';
import GameService from '@/entities/game/game.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Game Management Detail Component', () => {
    let wrapper: Wrapper<GameClass>;
    let comp: GameClass;
    let gameServiceStub: SinonStubbedInstance<GameService>;

    beforeEach(() => {
      gameServiceStub = sinon.createStubInstance<GameService>(GameService);

      wrapper = shallowMount<GameClass>(GameDetailComponent, { store, i18n, localVue, provide: { gameService: () => gameServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGame = { id: 123 };
        gameServiceStub.find.resolves(foundGame);

        // WHEN
        comp.retrieveGame(123);
        await comp.$nextTick();

        // THEN
        expect(comp.game).toBe(foundGame);
      });
    });
  });
});
