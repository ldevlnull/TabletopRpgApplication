import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class GameSystemUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('trpgPlanningApplicationApp.gameSystem.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  gameSystemNameInput: ElementFinder = element(by.css('input#game-system-gameSystemName'));

  descriptionInput: ElementFinder = element(by.css('input#game-system-description'));

  pictureURLInput: ElementFinder = element(by.css('input#game-system-pictureURL'));
}
