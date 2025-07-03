import React, { ReactNode, useState, useRef, useEffect } from 'react';
import styled from 'styled-components';

interface HorizontalPagerProps {
  children: ReactNode[];
  pageSize?: 'fill' | number;
  pageSpacing?: number;
  contentPadding?: { left: number; right: number };
  showPagination?: boolean;
}

const PagerContainer = styled.div`
  position: relative;
  width: 100%;
  overflow: hidden;
`;

const PagerContent = styled.div<{ 
  transform: string; 
  transition: string;
  pageSpacing: number;
  paddingLeft: number;
  paddingRight: number;
}>`
  display: flex;
  transition: ${props => props.transition};
  transform: ${props => props.transform};
  padding-left: ${props => props.paddingLeft}px;
  padding-right: ${props => props.paddingRight}px;
  & > * {
    margin-right: ${props => props.pageSpacing}px;
    &:last-child {
      margin-right: 0;
    }
  }
`;

const PageItem = styled.div<{ width: string }>`
  flex: 0 0 ${props => props.width};
  max-width: ${props => props.width};
`;

const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 16px;
`;

const PaginationDot = styled.div<{ active: boolean }>`
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: ${props => props.active 
    ? props.theme.colors.backgroundBrand 
    : props.theme.colors.backgroundSurface2};
  margin: 0 4px;
  cursor: pointer;
  transition: background-color 0.2s ease;
`;

const HorizontalPager: React.FC<HorizontalPagerProps> = ({
  children,
  pageSize = 'fill',
  pageSpacing = 16,
  contentPadding = { left: 16, right: 16 },
  showPagination = true,
}) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [isTransitioning, setIsTransitioning] = useState(false);
  const containerRef = useRef<HTMLDivElement>(null);
  const [containerWidth, setContainerWidth] = useState(0);

  useEffect(() => {
    const updateWidth = () => {
      if (containerRef.current) {
        setContainerWidth(containerRef.current.offsetWidth);
      }
    };

    updateWidth();
    window.addEventListener('resize', updateWidth);
    return () => window.removeEventListener('resize', updateWidth);
  }, []);

  const pageWidth = typeof pageSize === 'number' 
    ? `${pageSize}px` 
    : '100%';

  const handlePageChange = (pageIndex: number) => {
    if (pageIndex === currentPage || isTransitioning) return;
    
    setIsTransitioning(true);
    setCurrentPage(pageIndex);
    
    setTimeout(() => {
      setIsTransitioning(false);
    }, 300);
  };

  const transform = `translateX(-${currentPage * (containerWidth + pageSpacing)}px)`;
  const transition = isTransitioning ? 'transform 0.3s ease-out' : 'none';

  return (
    <div>
      <PagerContainer ref={containerRef}>
        <PagerContent 
          transform={transform}
          transition={transition}
          pageSpacing={pageSpacing}
          paddingLeft={contentPadding.left}
          paddingRight={contentPadding.right}
        >
          {children.map((child, index) => (
            <PageItem key={index} width={pageWidth}>
              {child}
            </PageItem>
          ))}
        </PagerContent>
      </PagerContainer>
      
      {showPagination && children.length > 1 && (
        <PaginationContainer>
          {children.map((_, index) => (
            <PaginationDot 
              key={index} 
              active={index === currentPage} 
              onClick={() => handlePageChange(index)}
            />
          ))}
        </PaginationContainer>
      )}
    </div>
  );
};

export default HorizontalPager;