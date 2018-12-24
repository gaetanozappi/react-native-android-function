import { NativeModules } from 'react-native';
import { pinnedShortcuts } from './pinnedShortcuts';
class AndroidFunction {}
AndroidFunction = NativeModules.AndroidFunction;
AndroidFunction.pinnedShortcuts = pinnedShortcuts;
export default AndroidFunction;
