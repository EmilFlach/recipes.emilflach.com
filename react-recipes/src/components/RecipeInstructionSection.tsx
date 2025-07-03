import React, { useState } from 'react';
import styled from 'styled-components';
import type { InstructionSection } from '../data/models';
import RecipeInstructionItem from './RecipeInstructionItem';

interface RecipeInstructionSectionProps {
  section: InstructionSection;
  isExpanded: boolean;
  onToggleExpanded: () => void;
  currentInstructionIndex: number;
  onInstructionClick: (index: number) => void;
  instructionRefs: React.MutableRefObject<Record<number, HTMLDivElement | null>>;
}

const SectionContainer = styled.div`
  margin-bottom: 16px;
`;

const SectionHeader = styled.div`
  display: flex;
  align-items: center;
  padding: 16px 24px;
  background-color: ${({ theme }) => theme.colors.backgroundSurface1};
  border-radius: 8px;
  cursor: pointer;
  margin-bottom: 8px;
`;

const SectionTitle = styled.h3`
  margin: 0;
  color: ${({ theme }) => theme.colors.foregroundBrand};
`;

const SectionSubtitle = styled.p`
  margin: 8px 0 0;
  color: ${({ theme }) => theme.colors.foregroundSupport};
  font-size: 0.875rem;
`;

const ExpandIcon = styled.span`
  margin-left: auto;
  font-size: 1.5rem;
  color: ${({ theme }) => theme.colors.foregroundSupport};
`;

const InstructionsContainer = styled.div`
  padding-left: 16px;
`;

const RecipeInstructionSection: React.FC<RecipeInstructionSectionProps> = ({
  section,
  isExpanded,
  onToggleExpanded,
  currentInstructionIndex,
  onInstructionClick,
  instructionRefs
}) => {
  return (
    <SectionContainer>
      <SectionHeader onClick={onToggleExpanded}>
        <div>
          <SectionTitle>{section.title}</SectionTitle>
          {section.subtitle && <SectionSubtitle>{section.subtitle}</SectionSubtitle>}
        </div>
        <ExpandIcon>{isExpanded ? '−' : '+'}</ExpandIcon>
      </SectionHeader>
      
      {isExpanded && (
        <InstructionsContainer>
          {section.instructions.map((instruction) => (
            <RecipeInstructionItem
              key={instruction.id}
              instruction={instruction}
              index={instruction.globalIndex}
              isCurrent={instruction.globalIndex === currentInstructionIndex}
              onClick={() => onInstructionClick(instruction.globalIndex)}
              ref={el => instructionRefs.current[instruction.globalIndex] = el}
            />
          ))}
        </InstructionsContainer>
      )}
    </SectionContainer>
  );
};

export default RecipeInstructionSection;