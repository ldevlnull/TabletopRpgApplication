import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class CharacterUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('trpgPlanningApplicationApp.character.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  characterNameInput: ElementFinder = element(by.css('input#character-characterName'));

  isAliveInput: ElementFinder = element(by.css('input#character-isAlive'));
  userSelect = element(by.css('select#character-user'));

  gameSystemSelect = element(by.css('select#character-gameSystem'));
}
