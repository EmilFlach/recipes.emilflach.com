import { css } from 'styled-components';

// Brand colors
export const BrandDarkest = '#2d3307';
export const BrandDarker = '#394109';
export const BrandDefault = '#464d0a';
export const BrandLighter = '#737942';
export const BrandLightest = '#a0a57a';

// Neutral palette
export const Neutral50 = '#f7f1e7';
export const Neutral100 = '#e3d8cf';
export const Neutral200 = '#cdbdb7';
export const Neutral300 = '#b7a29f';
export const Neutral400 = '#a18787';
export const Neutral500 = '#8b6c6f';
export const Neutral600 = '#755157';
export const Neutral700 = '#5f363f';
export const Neutral800 = '#491b27';
export const Neutral900 = '#33000f';

// Contextual colors
export const InfoLight = '#90CAF9';
export const Info = '#2196F3';
export const InfoDark = '#0D47A1';

export const SuccessLight = '#A5D6A7';
export const Success = '#4CAF50';
export const SuccessDark = '#1B5E20';

export const WarningLight = '#FFE082';
export const Warning = '#FFC107';
export const WarningDark = '#F57F17';

export const DangerLight = '#EF9A9A';
export const Danger = '#F44336';
export const DangerDark = '#B71C1C';

// Theme interface
export interface RecipesColors {
  // Background Colors
  backgroundPage: string;
  backgroundSurface1: string;
  backgroundSurface2: string;
  backgroundSurface1Hover: string;
  backgroundSurface1Pressed: string;

  backgroundBrand: string;
  backgroundBrandSubtle: string;
  backgroundBrandHover: string;
  backgroundBrandPressed: string;

  backgroundInfo: string;
  backgroundInfoSubtle: string;
  backgroundSuccess: string;
  backgroundSuccessSubtle: string;
  backgroundWarning: string;
  backgroundWarningSubtle: string;
  backgroundDanger: string;
  backgroundDangerSubtle: string;

  backgroundDisabled: string;
  backgroundSelected: string;
  backgroundLoading: string;

  // On Background Colors
  onBackgroundBrand: string;
  onBackgroundBrandSubtle: string;
  onBackgroundInfo: string;
  onBackgroundInfoSubtle: string;
  onBackgroundSuccess: string;
  onBackgroundSuccessSubtle: string;
  onBackgroundWarning: string;
  onBackgroundWarningSubtle: string;
  onBackgroundDanger: string;
  onBackgroundDangerSubtle: string;

  // Foreground Colors
  foregroundDefault: string;
  foregroundSupport: string;
  foregroundBrand: string;
  foregroundInfo: string;
  foregroundSuccess: string;
  foregroundWarning: string;
  foregroundDanger: string;
  foregroundDisabled: string;

  // Border Colors
  borderDefault: string;
  borderStrong: string;
  borderBrand: string;
  borderInfo: string;
  borderSuccess: string;
  borderWarning: string;
  borderDanger: string;
  borderDisabled: string;
  borderFocus: string;
  borderSeparator: string;

  // Link Colors
  linkDefault: string;
  linkHover: string;
  linkPressed: string;
  linkVisited: string;

  // Is Dark Theme
  isDark: boolean;
}

// Light theme colors
export const LightTheme: RecipesColors = {
  // Background Colors
  backgroundPage: Neutral50,
  backgroundSurface1: Neutral100,
  backgroundSurface2: Neutral200,
  backgroundSurface1Hover: Neutral200,
  backgroundSurface1Pressed: Neutral300,

  backgroundBrand: BrandDefault,
  backgroundBrandSubtle: BrandLightest,
  backgroundBrandHover: BrandDarker,
  backgroundBrandPressed: BrandDarkest,

  backgroundInfo: Info,
  backgroundInfoSubtle: `${InfoLight}26`, // 15% opacity
  backgroundSuccess: Success,
  backgroundSuccessSubtle: `${SuccessLight}26`, // 15% opacity
  backgroundWarning: Warning,
  backgroundWarningSubtle: `${WarningLight}26`, // 15% opacity
  backgroundDanger: Danger,
  backgroundDangerSubtle: `${DangerLight}26`, // 15% opacity

  backgroundDisabled: Neutral300,
  backgroundSelected: BrandLightest,
  backgroundLoading: Neutral300,

  // On Background Colors
  onBackgroundBrand: '#FFFFFF',
  onBackgroundBrandSubtle: BrandDefault,
  onBackgroundInfo: '#FFFFFF',
  onBackgroundInfoSubtle: Info,
  onBackgroundSuccess: '#FFFFFF',
  onBackgroundSuccessSubtle: Success,
  onBackgroundWarning: Neutral900,
  onBackgroundWarningSubtle: WarningDark,
  onBackgroundDanger: '#FFFFFF',
  onBackgroundDangerSubtle: Danger,

  // Foreground Colors
  foregroundDefault: Neutral900,
  foregroundSupport: Neutral700,
  foregroundBrand: BrandDefault,
  foregroundInfo: Info,
  foregroundSuccess: Success,
  foregroundWarning: Warning,
  foregroundDanger: Danger,
  foregroundDisabled: Neutral600,

  // Border Colors
  borderDefault: Neutral200,
  borderStrong: Neutral700,
  borderBrand: BrandDefault,
  borderInfo: Info,
  borderSuccess: Success,
  borderWarning: Warning,
  borderDanger: Danger,
  borderDisabled: Neutral300,
  borderFocus: Neutral200,
  borderSeparator: Neutral300,

  // Link Colors
  linkDefault: BrandDefault,
  linkHover: BrandDarker,
  linkPressed: BrandDarkest,
  linkVisited: BrandDarker,

  isDark: false,
};

// Dark theme colors
export const DarkTheme: RecipesColors = {
  // Background Colors
  backgroundPage: Neutral900,
  backgroundSurface1: Neutral800,
  backgroundSurface2: Neutral700,
  backgroundSurface1Hover: Neutral700,
  backgroundSurface1Pressed: Neutral600,

  backgroundBrand: BrandDefault,
  backgroundBrandSubtle: BrandDarker,
  backgroundBrandHover: BrandLighter,
  backgroundBrandPressed: BrandLightest,

  backgroundInfo: Info,
  backgroundInfoSubtle: `${Info}33`, // 20% opacity
  backgroundSuccess: Success,
  backgroundSuccessSubtle: `${Success}33`, // 20% opacity
  backgroundWarning: Warning,
  backgroundWarningSubtle: `${Warning}33`, // 20% opacity
  backgroundDanger: Danger,
  backgroundDangerSubtle: `${Danger}33`, // 20% opacity

  backgroundDisabled: Neutral600,
  backgroundSelected: BrandDarkest,
  backgroundLoading: Neutral600,

  // On Background Colors
  onBackgroundBrand: '#FFFFFF',
  onBackgroundBrandSubtle: BrandDefault,
  onBackgroundInfo: '#FFFFFF',
  onBackgroundInfoSubtle: InfoLight,
  onBackgroundSuccess: '#FFFFFF',
  onBackgroundSuccessSubtle: SuccessLight,
  onBackgroundWarning: Neutral900,
  onBackgroundWarningSubtle: WarningLight,
  onBackgroundDanger: '#FFFFFF',
  onBackgroundDangerSubtle: DangerLight,

  // Foreground Colors
  foregroundDefault: Neutral50,
  foregroundSupport: Neutral200,
  foregroundBrand: BrandDefault,
  foregroundInfo: InfoLight,
  foregroundSuccess: SuccessLight,
  foregroundWarning: WarningLight,
  foregroundDanger: DangerLight,
  foregroundDisabled: Neutral300,

  // Border Colors
  borderDefault: Neutral700,
  borderStrong: Neutral200,
  borderBrand: BrandDefault,
  borderInfo: InfoLight,
  borderSuccess: SuccessLight,
  borderWarning: WarningLight,
  borderDanger: DangerLight,
  borderDisabled: Neutral600,
  borderFocus: BrandDefault,
  borderSeparator: Neutral600,

  // Link Colors
  linkDefault: BrandDefault,
  linkHover: BrandLighter,
  linkPressed: BrandLightest,
  linkVisited: BrandDarker,

  isDark: true,
};