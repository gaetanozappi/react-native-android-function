import AntDesignGlyphMap from 'react-native-vector-icons/glyphmaps/AntDesign.json';
import EntypoGlyphMap from 'react-native-vector-icons/glyphmaps/Entypo.json';
import EvilIconsGlyphMap from 'react-native-vector-icons/glyphmaps/EvilIcons.json';
import FeatherGlyphMap from 'react-native-vector-icons/glyphmaps/Feather.json';
import FontAwesomeGlyphMap from 'react-native-vector-icons/glyphmaps/FontAwesome.json';
import FontAwesome5GlyphMap from 'react-native-vector-icons/glyphmaps/FontAwesome5Free.json';
import FoundationGlyphMap from 'react-native-vector-icons/glyphmaps/Foundation.json';
import IoniconsGlyphMap from 'react-native-vector-icons/glyphmaps/Ionicons.json';
import MaterialCommunityIconsGlyphMap from 'react-native-vector-icons/glyphmaps/MaterialCommunityIcons.json';
import MaterialIconsGlyphMap from 'react-native-vector-icons/glyphmaps/MaterialIcons.json';
import OcticonsGlyphMap from 'react-native-vector-icons/glyphmaps/Octicons.json';
import SimpleLineIconsGlyphMap from 'react-native-vector-icons/glyphmaps/SimpleLineIcons.json';
import ZocialGlyphMap from 'react-native-vector-icons/glyphmaps/Zocial.json';

export default class RNVectorHelper {
  static Resolve(family, name) {
    let glyph = undefined;
    switch (family) {
      case 'AntDesign': glyph = AntDesignGlyphMap[name]; break;
      case 'Entypo': glyph = EntypoGlyphMap[name]; break;
      case 'EvilIcons': glyph = EvilIconsGlyphMap[name]; break;
      case 'Feather': glyph = FeatherGlyphMap[name]; break;
      case 'FontAwesome': glyph = FontAwesomeGlyphMap[name]; break;
      case 'FontAwesome5': glyph = FontAwesome5GlyphMap[name]; break;
      case 'Foundation': glyph = FoundationGlyphMap[name]; break;
      case 'Ionicons': glyph = IoniconsGlyphMap[name]; break;
      case 'MaterialCommunityIcons': glyph = MaterialCommunityIconsGlyphMap[name]; break;
      case 'MaterialIcons': glyph = MaterialIconsGlyphMap[name]; break;
      case 'Octicons': glyph = OcticonsGlyphMap[name]; break;
      case 'SimpleLineIcons': glyph = SimpleLineIconsGlyphMap[name]; break;
      case 'Zocial': glyph = ZocialGlyphMap[name]; break;
    }
    if (typeof glyph === 'number') glyph = String.fromCharCode(glyph);
    return { glyph: glyph, family };
  }
}
