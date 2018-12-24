import React, { PureComponent } from 'react';
import { ViewPropTypes, NativeModules } from 'react-native';
import PropTypes from 'prop-types';
import RNVectorHelper from './RNVectorHelper';
const { PinnedShortcuts } = NativeModules;

class pinnedShortcuts extends PureComponent {
  static propTypes = { ...ViewPropTypes };
  static defaultProps = {};
  static setShortcutItems(props) {
    props = props.map(el => {
      if (el.icon && el.icon.family && el.icon.name) {
        let vectorIcon = RNVectorHelper.Resolve(el.icon.family, el.icon.name);
        el.icon = vectorIcon.glyph === undefined ? {} : Object.assign({}, el.icon, vectorIcon);
      } else {
        el.icon = {};
      }
      return el;
    });
    PinnedShortcuts.addPinnedShortcuts(props);
  }

  static popInitialAction() {
    return PinnedShortcuts.popInitialAction()
      .then(data => {
        return new Promise((resolve, reject) => {
          resolve(data);
        });
      })
      .catch(console.error);
  }

  render() {
    return null;
  }
}

export { pinnedShortcuts };
