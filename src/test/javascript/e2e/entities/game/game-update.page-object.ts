import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class GameUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('trpgPlanningApplicationApp.game.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  gameNameInput: ElementFinder = element(by.css('input#game-gameName'));

  playDateInput: ElementFinder = element(by.css('input#game-playDate'));

  playersLimitInput: ElementFinder = element(by.css('input#game-playersLimit'));

  pictureURLInput: ElementFinder = element(by.css('input#game-pictureURL'));

  descriptionInput: ElementFinder = element(by.css('input#game-description'));

  statusSelect = element(by.css('select#game-status'));
  gameSystemSelect = element(by.css('select#game-gameSystem'));

  tagsSelect = element(by.css('select#game-tags'));

  charactersSelect = element(by.css('select#game-characters'));
}
